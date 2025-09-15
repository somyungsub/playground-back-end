import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

//  @Test
//  void 문자열추출() throws IOException {
//    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"(.*?)\"\\s*(?:,|\\))");
//    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");
//    System.out.println(path);
////    System.out.println(Files.isDirectory(path));
////    Files.list(path).forEach(System.out::println);
//    try (Stream<String> lines = Files.lines(path)) {
//      Files.lines(path).forEach(System.out::println);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }

  @Test
  void 문자열추출() throws IOException {
    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"(.*?)\"\\s*(?:,|\\))");

    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");

    try (Stream<String> lines = Files.lines(path)) {
      List<String> messages = lines
              .map(String::trim)
              .map(line -> {
                Matcher matcher = MESSAGE_PATTERN.matcher(line);
                if (matcher.find()) {
                  return matcher.group(1);
                }
                return null;
              })
              .filter(Objects::nonNull)
              .toList();

      // 결과 출력
      for (int i = 0; i < messages.size(); i++) {
        String key = String.format("HIS-OLD-%04d", i + 1);
        System.out.println(key + "=" + messages.get(i));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void 문자열추출_중복제거() throws IOException {
    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"(.*?)\"\\s*(?:,|\\))");

    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");

    Set<String> uniqueMessages = new LinkedHashSet<>();

    try (Stream<String> lines = Files.lines(path)) {
      lines.map(String::trim)
              .forEach(line -> {
                Matcher matcher = MESSAGE_PATTERN.matcher(line);
                if (matcher.find()) {
                  uniqueMessages.add(matcher.group(1));
                }
              });
    }

    int index = 1;
    for (String message : uniqueMessages) {
      String key = String.format("HIS-OLD-%04d", index++);
      System.out.println(key + "=" + message);
    }
  }


  @Test
  void 문자열리터럴만추출() throws IOException {
    // "..." 형식의 문자열만 추출
    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"([^\"]+)\"\\s*(?:,|\\))");

    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");

    Set<String> uniqueMessages = new LinkedHashSet<>();

    try (Stream<String> lines = Files.lines(path)) {
      lines.map(String::trim)
              .forEach(line -> {
                Matcher matcher = MESSAGE_PATTERN.matcher(line);
                if (matcher.find()) {
                  String message = matcher.group(1).trim();

                  // message가 변수명이나 연산이 아닌 순수 문자열인지 추가 검증
                  if (!message.contains("+") && !message.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    uniqueMessages.add(message);
                  }
                }
              });
    }

    int index = 1;
    for (String message : uniqueMessages) {
      String key = String.format("HIS-OLD-%04d", index++);
      System.out.println(key + "=" + message);
    }
  }

  @Test
  void 문자열리터럴만_유효한것만추출() throws IOException {
    // "..." 형식의 문자열만 추출
    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"([^\"]+)\"\\s*(?:,|\\))");

    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");

    Set<String> uniqueMessages = new LinkedHashSet<>();

    try (Stream<String> lines = Files.lines(path)) {
      lines.map(String::trim)
              .forEach(line -> {
                Matcher matcher = MESSAGE_PATTERN.matcher(line);
                if (matcher.find()) {
                  String message = matcher.group(1).trim();

                  // 조건 1: 빈 문자열 아님
                  // 조건 2: 연산(+)이 포함되지 않음
                  // 조건 3: HIS-COM 으로 시작하지 않음
                  if (!message.isBlank()
                          && !message.contains("+")
                          && !message.startsWith("HIS-COM")) {
                    uniqueMessages.add(message);
                  }
                }
              });
    }

    int index = 1;
    for (String message : uniqueMessages) {
      String key = String.format("HIS-OLD-%04d", index++);
      System.out.println(key + "=" + message);
    }
  }
  @Test
  void 순수문자열만추출_HIS제외() throws IOException {
    Pattern MESSAGE_PATTERN = Pattern.compile("ServiceException\\.of\\(\\s*\"([^\"]+)\"\\s*(?:,|\\))");

    Path path = Paths.get("/Users/myungsubso/Desktop/github-test/my/playground-back-end/prototype-hexagonal/module-test/jdk-test/src/test/resources/result.txt");

    Set<String> uniqueMessages = new LinkedHashSet<>();

    try (Stream<String> lines = Files.lines(path)) {
      lines.map(String::trim)
              .forEach(line -> {
                Matcher matcher = MESSAGE_PATTERN.matcher(line);
                if (matcher.find()) {
                  String message = matcher.group(1).trim();

                  // 1. 빈 문자열 아님
                  // 2. 연산 포함 안함
                  // 3. HIS-로 시작하지 않음
                  if (!message.isBlank()
                          && !message.contains("+")
                          && !message.startsWith("HIS-")) {
                    uniqueMessages.add(message);
                  }
                }
              });
    }

    int index = 1;
    for (String message : uniqueMessages) {
      String key = String.format("HIS-OLD-%04d", index++);
      System.out.println(key + "=" + message);
    }
  }




}
