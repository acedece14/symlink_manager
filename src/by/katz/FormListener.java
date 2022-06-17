package by.katz;

import java.io.File;

public interface FormListener {

    void onFileSelected(File selectedFile);

    void onDirSelected(File selectedFile);

    void onTextEntered(String text);
}
