package com.hari.config;


import com.hari.model.KnowledgeArticle;
import com.hari.repository.KnowledgeBaseRepository;
import com.hari.service.RagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer
        implements CommandLineRunner {

    private final KnowledgeBaseRepository repository;

    private final RagService ragService;


    public DataInitializer(

            KnowledgeBaseRepository repository,

            RagService ragService

    ) {

        this.repository =
                repository;

        this.ragService =
                ragService;
    }


    @Override
    public void run(
            String... args
    ) {

        loadKnowledgeBase();
    }


    private void loadKnowledgeBase() {

        createVpnArticle();

        createPasswordArticle();

        createOutlookArticle();

        createPrinterArticle();
    }


    private void createVpnArticle() {

        KnowledgeArticle article =

                new KnowledgeArticle(

                        "KB-1001",

                        "VPN Connection Failure",

                        "Network",

                        "VPN",

                        "User is unable to connect to "
                                + "the corporate VPN.",

                        List.of(

                                "Verify that the user has "
                                        + "an active internet connection.",

                                "Restart the VPN client.",

                                "Clear the VPN client cache.",

                                "Restart the system.",

                                "Open the VPN client and "
                                        + "connect again.",

                                "If the VPN still fails, "
                                        + "capture the VPN error "
                                        + "message and escalate "
                                        + "to the network support team."
                        )
                );


        save(article);
    }


    private void createPasswordArticle() {

        KnowledgeArticle article =

                new KnowledgeArticle(

                        "KB-1002",

                        "Corporate Password Reset",

                        "Account",

                        "Password",

                        "User has forgotten the corporate "
                                + "password or needs to reset it.",

                        List.of(

                                "Open the corporate "
                                        + "password portal.",

                                "Select the Forgot Password "
                                        + "option.",

                                "Enter the corporate username.",

                                "Complete identity verification.",

                                "Create a new password.",

                                "Sign in again using the "
                                        + "new password."
                        )
                );


        save(article);
    }


    private void createOutlookArticle() {

        KnowledgeArticle article =

                new KnowledgeArticle(

                        "KB-1003",

                        "Outlook Application Not Opening",

                        "Application",

                        "Outlook",

                        "Microsoft Outlook does not open "
                                + "or becomes unresponsive.",

                        List.of(

                                "Close Outlook completely.",

                                "Open Task Manager and verify "
                                        + "that Outlook is not running.",

                                "Restart the computer.",

                                "Start Outlook again.",

                                "If Outlook still does not open, "
                                        + "create a new Outlook profile.",

                                "If the problem continues, "
                                        + "escalate to the application "
                                        + "support team."
                        )
                );


        save(article);
    }


    private void createPrinterArticle() {

        KnowledgeArticle article =

                new KnowledgeArticle(

                        "KB-1004",

                        "Office Printer Not Printing",

                        "Hardware",

                        "Printer",

                        "User is unable to print documents "
                                + "to the configured office printer.",

                        List.of(

                                "Verify that the printer is powered on.",

                                "Verify that the printer is connected "
                                        + "to the corporate network.",

                                "Check whether the printer has "
                                        + "paper and toner.",

                                "Clear any pending print jobs.",

                                "Restart the Print Spooler service.",

                                "Send a test print.",

                                "If printing still fails, "
                                        + "escalate to hardware support."
                        )
                );


        save(article);
    }


    private void save(
            KnowledgeArticle article
    ) {

        repository.save(article);

        ragService.indexArticle(article);
    }
}
