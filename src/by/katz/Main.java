package by.katz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main implements FormListener {

    private static String path;
    private static FormMain formMain;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] args) {
        Settings.get().loadSettings();
        if (args != null && args.length != 0) {
            path = args[0];
            Settings.get().setLastTarget(path);
        }
        formMain = new FormMain(this);
    }

    @Override public void onFileSelected(File selectedFile) {
        System.out.println("onFileSelected " + selectedFile);
        if (selectedFile.isDirectory()) {
            showError(selectedFile.getAbsolutePath() + " is directory");
            return;
        }
        try {
            Files.createSymbolicLink(selectedFile.toPath(), new File(path).toPath());
            formMain.showInfo("Created: " + selectedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getLocalizedMessage());
        }
    }

    private void showError(String errorText) {
        formMain.showError(errorText);
    }

    @Override public void onDirSelected(File selectedFile) {
        System.out.println("onDirSelected " + selectedFile);
        if (!selectedFile.isDirectory()) {
            showError(selectedFile.getAbsolutePath() + " is not directory");
            return;
        }
        try {
            Files.createSymbolicLink(selectedFile.toPath(), new File(path).toPath());
            formMain.showInfo("Created: " + selectedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getLocalizedMessage());
        }

    }

    @Override public void onTextEntered(String path) {
        System.out.println("onTextEntered " + path);
        try {
            var file = new File(path);
            Files.createSymbolicLink(file.toPath(), new File(Main.path).toPath());
            formMain.showInfo("Created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getLocalizedMessage());
        }

    }
}
