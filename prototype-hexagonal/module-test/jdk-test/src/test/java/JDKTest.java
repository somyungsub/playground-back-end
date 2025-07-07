import com.google.common.math.IntMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class JDKTest {

  @Test
  void jdk8_optional_ofNullable() {
    Optional<Object> o = Optional.ofNullable(null);
    print(o);
  }

  @Test
  void jdk9_processHandle() {
    ProcessHandle processHandle = ProcessHandle.current();
    print(processHandle.pid());
    print(processHandle.info());
    print(processHandle.isAlive());
    print(processHandle.parent());
  }

  @Test
  void jdk9_newApi() {
    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    List<Integer> result = numbers.stream()
//            .limit(5)
            .takeWhile(integer -> integer <= 5)
            .toList();
    print(result);

    List<Integer> result2 = numbers.stream()
            .dropWhile(integer -> integer <= 3)
            .toList();
    print(result2);

    String nullData = null;
    String valueStr = "Hello";

    Optional.ofNullable(valueStr)
            .ifPresentOrElse(
                    System.out::println,
                    () -> {
                      throw new RuntimeException("빈데이터");
                    }
            );

    Optional.ofNullable(nullData)
            .ifPresentOrElse(
                    System.out::println,
                    () -> {
                      throw new RuntimeException("빈데이터");
                    }
            );
  }

  //  @ParameterizedTest
//  @ValueSource()
  @Test
//  void jdk12_number_format(Locale locale, NumberFormat.Style style, long[] intList) {
  void jdk12_number_format() {
    NumberFormat shortNumberFormat = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
    print(shortNumberFormat.format(1000));
    print(shortNumberFormat.format(1_000_000));
    print(shortNumberFormat.format(1_000_000_000));
    print(shortNumberFormat.format(1_000_000_000_000L));
    print(shortNumberFormat.format(1_000_000_000_000_000L));

    NumberFormat longNumberFormat = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);
    print(longNumberFormat.format(1000));
    print(longNumberFormat.format(1_000_000));
    print(longNumberFormat.format(1_000_000_000));
    print(longNumberFormat.format(1_000_000_000_000L));
    print(longNumberFormat.format(1_000_000_000_000_000L));

    NumberFormat shorNumberFormatKR = NumberFormat.getCompactNumberInstance(Locale.KOREA, NumberFormat.Style.SHORT);
    print(shorNumberFormatKR.format(1000));
    print(shorNumberFormatKR.format(1_000_000));
    print(shorNumberFormatKR.format(1_000_000_000));
    print(shorNumberFormatKR.format(1_000_000_000_000L));
    print(shorNumberFormatKR.format(1_000_000_000_000_000L));

    NumberFormat longNumberFormatKR = NumberFormat.getCompactNumberInstance(Locale.KOREA, NumberFormat.Style.LONG);
    print(longNumberFormatKR.format(1000));
    print(longNumberFormatKR.format(1_000_000));
    print(longNumberFormatKR.format(1_000_000_000));
    print(longNumberFormatKR.format(1_000_000_000_000L));
    print(longNumberFormatKR.format(1_000_000_000_000_000L));

    NumberFormat currencyInstanceUS = NumberFormat.getCurrencyInstance(Locale.US);
    print(currencyInstanceUS.format(1000));

    NumberFormat currencyInstanceKR = NumberFormat.getCurrencyInstance(Locale.KOREA);
    print(currencyInstanceKR.format(1000));

  }

  @Test
  void jdk16_mapMulti() {
    List<String> strings = List.of("a", "b", "c");
    List<String> result = strings.stream()
            .<String>mapMulti((element, consumer) -> {
              consumer.accept(element.toUpperCase());
              consumer.accept(element.toLowerCase());
              consumer.accept(StringUtils.join(element.toLowerCase(), "-", element.toUpperCase()));
            })
            .toList();
    System.out.println(result);
  }

  @Test
  void jdk16_mapMulti2() {
    List<Integer> strings = List.of(1, 2, 3, 4, 5);
    List<Integer> result = strings.stream()
            .<Integer>mapMulti((value, consumer) -> {

              if (IntMath.mod(value, 2) == 0) {
                consumer.accept(value * 2);
              } else {
                consumer.accept(value);
              }
            })
            .toList();

    print(result);
  }

  @Test
  void 주석처리() throws IOException {
    List<Integer> integers = List.of(
            10000022
            , 10000021
            , 10000023
            , 10000024
            , 10236022
            , 10236021
            , 10236023
            , 10236024
            , 10236020
            , 10236025
            , 10236026
            , 10236030
            , 10236027
            , 10236028
            , 10236029
            , 10236032
            , 10236033
            , 10236034
            , 10236035
            , 10236036
            , 10236037
            , 10236038
            , 10236039
            , 10236040
            , 10236041
            , 10236042
            , 10236044
            , 10236045
            , 10236046
            , 10236047
            , 10236043
            , 10236049
            , 10236048
            , 10236050
            , 10236051
            , 10236052
            , 10236053
            , 10236054
            , 10236061
            , 10236055
            , 10236056
            , 10236058
            , 10236057
            , 10236059
            , 10236060
            , 10236018
            , 10236017
    );

    // 정규식 패턴 생성
    String patternString = integers.stream()
            .map(Object::toString)
            .reduce((a, b) -> a + "|" + b)
            .orElse("");

    Pattern pattern = Pattern.compile("\\b(" + patternString + ")\\b");
//    Matcher matcher = pattern.matcher(input);

    System.out.println(integers.size());
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion_modified.sql");
//    Files.lines(path)
//            .filter(line -> {
//              Matcher matcher = pattern.matcher(line);
//              while (matcher.find()) {
//                return line;
//              }
//            });
    try (Stream<String> lines = Files.lines(path)) {
      // 파일 라인 필터링 및 매칭
      List<String> list = lines.filter(line -> {
        Matcher matcher = pattern.matcher(line);
        return matcher.find(); // 매칭되는 줄만 필터링
      }).toList();
      System.out.println(list.size());
      list.forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }


    try {
      // 파일 읽기 및 매칭된 문자열 변환
      List<String> modifiedLines = Files.lines(path)
              .map(line -> {
                Matcher matcher = pattern.matcher(line);
                // 매칭된 문자열에 "--" 추가
//                StringBuffer modifiedLine = new StringBuffer();
//                while (matcher.find()) {
//                  matcher.appendReplacement(modifiedLine, "--" + line);
//                }
//                matcher.appendTail(modifiedLine);
//                return modifiedLine.toString();
                if (matcher.find()) {
                  return "--" + line;
                }
                return line;
              })
              .toList(); // 결과를 리스트로 저장

      // 결과를 파일에 다시 쓰기
      Files.write(path, modifiedLines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void 단어갯수확인() throws IOException {
//    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_word_noversion.sql");
//    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion.sql");
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion_modified.sql");
    long insertInto = Files.lines(path)
            .filter(line -> StringUtils.startsWith(line, "INSERT INTO"))
            .count();
    System.out.println(insertInto);
  }

  @Test
  void 버전없는거() throws IOException {
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion_modified.sql");
    List<String> list = Files.lines(path)
            .filter(line -> StringUtils.startsWith(line, "--INSERT INTO"))
            .map(line -> StringUtils.replace(line, "--", ""))
            .toList();
//    System.out.println(list.size());
//    System.out.println(list);
    list.forEach(System.out::println);
  }

  @Test
  void str_test() {
//    String testInsert = "INSERT INTO his.tbl_meta_glossary (glossary_id, create_date, creator_id, modifier_id, update_date, use_yn, abbr, code_key, custom_data_type, description, display_name, domain_data_type, domain_group_flag, full_name, glossary_name, group_flag, locale_code, original_abbr, version, hist_seq) VALUES (19931, '2023-05-21 23:28:31.742676', 'thsong', 'thsong', '2023-05-21 23:28:31.742676', 'Y', 'INS_KNDCD_ER_YN', '0/1', 'varchar(1)', '보험종류코드오류여부', '', '', '0', '보험종류코드오류여부', '보험종류코드오류여부', '1', 'KO', '', 0, 0);";
    String testInsert = "장기미접속 계정잠김 해제 시 문자인증 성공여부 저장";
    String[] split = testInsert.split(",");
    String lastStr = split[split.length - 1];
    System.out.println(StringUtils.endsWith(lastStr, ";"));
  }

  @Test
  void version_삭제() throws IOException {
//    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_word_noversion.sql");
//    Path outputPath = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_word_noversion_modified.sql");
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion.sql");
    Path outputPath = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/out_tbl_meta_glossary_noversion_modified.sql");
    System.out.println("path : " + path);
    try (var lines = Files.lines(path);
         var writer = Files.newBufferedWriter(outputPath)
    ) {
      lines.forEach(line -> {
        String replace = line.replace("version,", "");
        String[] split = replace.split(",");
        String lastStr = split[split.length - 1];
        if (StringUtils.endsWith(lastStr, ";")) {
//          String s1 = split[split.length - 2];
//          System.out.println("" + split.length + ":" + split[split.length - 2]);
          try {
            String result = Arrays.stream(split)
                    .filter(val -> !val.equals(split[split.length - 2])) // 마지막 두 번째 값 제외
                    .collect(Collectors.joining(","));
//            System.out.println(result);
            writer.write(result);
            writer.newLine();
          } catch (Exception e) {
            System.out.println("에러확인 : " + line);
            System.out.println(e);
            throw new RuntimeException(e);
          }
        } else {
          try {
            System.out.println("확인 : " + line);
            writer.write(replace);
            writer.newLine();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      });
    }
  }

  @Test
  void 스키마변경() throws IOException {
    Path path = Paths.get("/Users/myungsubso/Desktop/hanwha/02.db/his_3.0/hms2.0_mig/");
//    System.out.println(path);
//    System.out.println(Files.isDirectory(path));
//    Files.list(path).forEach(System.out::println);
//    System.out.println(Files.walk(path).filter(Files::isRegularFile).count());
//    System.out.println(Files.list(path).count());

//    Files.walk(path)
//            .filter(Files::isRegularFile)
//            .forEach(filePath -> {
//              if (!StringUtils.equals(filePath.getFileName().toString(), ".DS_Store")) {
//                System.out.println(filePath.getFileName());
//                System.out.println(filePath);
//              }
//            });


    AtomicInteger count = new AtomicInteger();
    Files.walk(path)
            .filter(Files::isRegularFile)
            .forEach(inputFilePath -> {
              // 스트림 방식으로 파일 읽기/쓰기
              if (!StringUtils.equals(inputFilePath.getFileName().toString(), ".DS_Store")) {
                count.getAndIncrement();
                Path outputFilePath = Path.of(path.toString(), "out_" + inputFilePath.getFileName().toString());
                try (var lines = Files.lines(inputFilePath);
                     var writer = Files.newBufferedWriter(outputFilePath);) {
                  System.out.println(inputFilePath.getFileName());
                  System.out.println(inputFilePath);
                  lines.map(line -> line.replace("INSERT INTO hms", "INSERT INTO his")) // 변환
                          .forEach(transformedLine -> {
                            try {
                              writer.write(transformedLine);
                              writer.newLine();
                            } catch (IOException e) {
                              throw new RuntimeException(e);
                            }
                          });
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              }
            });
    System.out.println(count);
  }

  @Test
  void jdk21_virtual_thread() {

    Runnable runnable = () -> {
      System.out.println(Thread.currentThread());
      System.out.println(Thread.currentThread().getName());
    };

//    Thread.ofPlatform()
//            .name("platform-thread")
//            .start(runnable);
//
//    Thread.ofVirtual()
//            .name("virtual-thread")
//            .start(runnable);
//
//    Thread.startVirtualThread(() -> {
//      System.out.println(Thread.currentThread());
//    });
//
//    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
//    executorService.submit(() -> {
//      System.out.println(Thread.currentThread());
//    });

  }

  <T> void print(T data) {
    log.info("{}", data);
  }

}
