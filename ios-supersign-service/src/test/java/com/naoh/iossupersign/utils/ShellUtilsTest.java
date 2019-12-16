package com.naoh.iossupersign.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class ShellUtilsTest {

    @Test
    public void testRunShell() throws IOException, InterruptedException {
        URL url = this.getClass().getClassLoader().getResource("sh/mobileconfig.sh");
        ShellUtils.run(url.getPath());
    }

}
