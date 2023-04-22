package co.jp.groves.app.api;

import java.io.FileInputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/images")
@Slf4j
class ImageController {

    private final String imagesDir;

    ImageController(@Value("${images.dir}") final String imagesDir) {
        this.imagesDir = imagesDir;
    }

    @GetMapping(value = "/{group}/{baseName}.{ext}")
    void getImage(
            @PathVariable String group,
            @PathVariable String baseName,
            @PathVariable String ext,
            HttpServletResponse response) {

        var dir = /*System.getProperty("user.home") +*/ imagesDir + "/" + group;

        try (FileInputStream input = new FileInputStream(FilenameUtils.concat(dir, baseName + "." + ext))) {
            if ("png".equalsIgnoreCase(ext)) {
                response.setContentType("image/png");
            } else if ("jpg".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext)) {
                response.setContentType("image/jpeg");
            }
            IOUtils.copy(input, response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
