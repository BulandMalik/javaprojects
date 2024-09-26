/*
 * Copyright 2015-2019 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.buland.example.springboot.banner.SpringBootBannerExamples;

import java.io.InputStream;
import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * More efficient Banner implementation which doesn't use property sources as variables are expanded
 * at compile time using Maven resource filtering.
 */
public class BannerFromFIle implements Banner {
  static final AnsiElement ORANGE = new AnsiElement() {
    @Override public String toString() {
      return "38;5;208"; // Ansi 256 color code 208 (orange)
    }
  };

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
    try (InputStream stream = new ClassPathResource("banner-coloured-1.txt").getInputStream()) {
      String banner = StreamUtils.copyToString(stream, UTF_8);

      // Instead of use property expansion for only 2 ansi codes, inline them
      banner = banner.replace("${AnsiOrange}", AnsiOutput.encode(ORANGE));
      banner = banner.replace("${AnsiNormal}", AnsiOutput.encode(AnsiStyle.NORMAL));

      out.println(banner);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}