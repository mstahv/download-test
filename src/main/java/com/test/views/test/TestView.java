package com.test.views.test;

import com.test.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.vaadin.firitin.components.DynamicFileDownloader;

import java.io.File;
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

        downloadButton.setDisableOnClick(true);
        downloadButton.addDownloadFinishedListener(e -> {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Download finished successfully");
            setEnabled(true);
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