package by.katz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main implements FormListener {

    private static FormMain formMain;

    public static void main(String[] args) { new Main(args); }

    public Main(String[] args) {
        Settings.init();
        if (args != null && args.length != 0)
            Settings.get().setLastTarget(args[0]);
        formMain = new FormMain(this);
    }

    private void showError(String errorText) {
        formMain.showError(errorText);
    }

    @Override public void onFileSelected(File selectedFile) {
        System.out.println("onFileSelected " + selectedFile);
        if (selectedFile.isDirectory()) {
            showError(selectedFile.getAbsolutePath() + " is directory");
            return;
        }
        try {
            Files.createSymbolicLink(selectedFile.toPath(), new File(Settings.get().getLastTarget()).toPath());
            formMain.showInfo("Created: " + selectedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getLocalizedMessage());
        }
    }

    @Override public void onDirSelected(File selectedFile) {
        System.out.println("onDirSelected " + selectedFile);
        try {
            Files.createSymbolicLink(selectedFile.toPath(), new File(Settings.get().getLastTarget()).toPath());
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
            Files.createSymbolicLink(file.toPath(), new File(Settings.get().getLastTarget()).toPath());
            formMain.showInfo("Created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError(e.getLocalizedMessage());
        }
    }
}
