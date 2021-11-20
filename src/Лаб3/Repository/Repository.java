package Лаб3.Repository;

import Лаб3.Entities.FileDesc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Repository {


    void add(List<File> files) throws IOException;


}
