package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 웹서버는 사용자가 요청한 html 파일을 제공 할 수 있어야 한다.
 * File 클래스를 사용해서 파일을 읽어오고, 사용자에게 전달한다.
 */
@DisplayName("File 클래스 학습 테스트")
class FileTest {

    /**
     * resource 디렉터리 경로 찾기
     *
     * File 객체를 생성하려면 파일의 경로를 알아야 한다.
     * 자바 애플리케이션은 resource 디렉터리에 HTML, CSS 같은 정적 파일을 저장한다.
     * resource 디렉터리의 경로는 어떻게 알아낼 수 있을까?
     */
    @Test
    void resource_디렉터리에_있는_파일의_경로를_찾는다() throws FileNotFoundException {

        final String fileName = "nextstep.txt";

        // todo
        final String currentDirectory = new File("").getAbsolutePath();

        var file = new File(currentDirectory);
        for (File foo: Objects.requireNonNull(file.listFiles())) {
            File foundFile = findFile(foo, fileName);
            if (foundFile != null) {
                assertThat(foundFile.getName()).isEqualTo(fileName);
                break;
            }
        }

    }


    /**
     * 파일 내용 읽기
     *
     * 읽어온 파일의 내용을 I/O Stream을 사용해서 사용자에게 전달 해야 한다.
     * File, Files 클래스를 사용하여 파일의 내용을 읽어보자.
     */
    @Test
    void 파일의_내용을_읽는다() throws IOException, URISyntaxException {

        final String fileName = "nextstep.txt";

        // todo
        URL resource = getClass().getClassLoader().getResource(fileName);
        assertThat(resource).isNotNull();

        try (
            var br = Files.newBufferedReader(new File(resource.toURI()).toPath());
        ) {
            assertThat(br.readLine()).isEqualTo("nextstep");
        }
    }


    private File findFile(File file, String fileName) {
        if (Objects.isNull(file.listFiles())) {
            return null;
        }

        for (File foo : Objects.requireNonNull(file.listFiles())) {
            if (foo.isDirectory()) {
                findFile(foo, fileName);
            }
            if (foo.getName().equals(fileName)) {
                return foo;
            }
        }
        return null;
    }

}
