import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileIOTest {

  @Test
  void SQL텍스트변환() throws IOException {
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/");
    System.out.println(path);
    System.out.println(Files.isDirectory(path));
    Files.list(path).forEach(System.out::println);
//    Files.walk(path)
//            .filter(Files::isRegularFile)
//            .forEach(file -> {
//              try {
//                String content = Files.readString(file);
//                System.out.println();
////                String replace = content.replace("INTO hbm.", "");
////                System.out.println();
////                Files.writeString(file, replace);
//              } catch (IOException e) {
//                throw new RuntimeException(e);
//              }
//            });
  }
}
