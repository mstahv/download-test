package com.test.views.test;

import com.test.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.IOUtils;
import org.vaadin.firitin.components.DynamicFileDownloader;

import java.io.IOException;

@PageTitle("Test")
@Route(value = "test", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TestView extends VerticalLayout
{
    public TestView()
    {
        DynamicFileDownloader downloadButton = new DynamicFileDownloader("Test", "tempFilename", null) {
            @Override
            protected String getFileName(VaadinSession session, VaadinRequest request) {
                return "CustomFilename";
            }
        };

        UI ui = UI.getCurrent();
        ui.setPollInterval(1000); // or @Push to AppShellConfigurator
        downloadButton.setDisableOnClick(true);
        downloadButton.addDownloadFinishedListener(e -> {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Download finished successfully");
            e.getSource().setEnabled(true);
            // or
            downloadButton.setEnabled(true);
            // This is not needed, plain setEnabled references the class where this lambda is defined, TestView
            // setEnabled(true)
        });

        downloadButton.addDownloadFailedListener(e -> {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx Download failed");
            Notification.show("There was an error downloading the file");
//            setEnabled(true);
        });

        downloadButton.setFileHandler(outputStream ->
        {
            try {
                outputStream.write(
                        IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("pdf/example.pdf")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add(downloadButton.asButton());
    }
}