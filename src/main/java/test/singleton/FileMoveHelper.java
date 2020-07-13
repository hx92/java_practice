package test.singleton;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileMoveHelper {

  /**
   * 将视频抽出来
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Path dir = Paths.get("D:\\BaiduNetdiskDownload\\咕泡学院三期(1)\\");
    String vDir = "D:\\BaiduNetdiskDownload\\咕泡学院三期(1)";
    File destDir = Paths.get(vDir + "\\target").toFile();
    destDir.mkdirs();
//    Stream<Path> list = Files.list(dir);
    Stream<Path> walk = Files.walk(dir, 10);
    AtomicInteger i = new AtomicInteger();
    walk.forEach(c -> {
      String name = c.getFileName().toString();
      if (name.endsWith(".url") || name.endsWith(".txt")) {
        //noinspection ResultOfMethodCallIgnored
        c.toFile().delete();
      }
      if (c.getNameCount() == dir.getNameCount() + 1) {
        return;
      }
      if (c.toFile().getAbsolutePath().contains("target")) {
        return;
      }
      if (c.toFile().isDirectory()) {
        return;
      }
      System.out.println((i.incrementAndGet()) + ":");
      String newNameNoSuffix;
      if (name.matches("^[_\\- 0-9a-zA-Z.]+$")) {
        System.out.println(c.getParent() + "\\" + c.getFileName());
        newNameNoSuffix = c.getParent().getFileName() + name.substring(name.lastIndexOf("."));
        System.out.println(newNameNoSuffix);
      } else {
        newNameNoSuffix = name;
      }
      File file = new File(destDir, newNameNoSuffix);
      System.out.println(file);
      System.out.println();
      c.toFile().renameTo(file);
    });


  }
}
