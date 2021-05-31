package org.xinc.code;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.Properties;

public class Main {


    public static void main(String[] args) {
        try {

            CodeProperty codeProperty = new CodeProperty("/application.properties");
            Repository newlyCreatedRepo = FileRepositoryBuilder.create(
                    new File(codeProperty.workspace + "/project/trunk/new_repo/.git"));
            newlyCreatedRepo.create(true);
            System.out.println(newlyCreatedRepo.getDirectory().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
