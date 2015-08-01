/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.britter.beanvalidators.file;

import java.io.File;

import com.github.britter.beanvalidators.ValidationWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DirectoryTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();
    private FileBean fileBean;
    private ValidationWrapper<FileBean> validator;

    @Before
    public void setUp() {
        fileBean = new FileBean();
        validator = new ValidationWrapper<>(fileBean, "must be a directory");
    }

    @Test
    public void shouldValidateNull() throws Exception {
        fileBean.dir = null;

        validator.assertNoViolations("dir");
    }

    @Test
    public void shouldValidateDirectory() throws Exception {
        fileBean.dir = tmpFolder.newFolder();

        validator.assertNoViolations("dir");
    }

    @Test
    public void shouldNotValidateFile() throws Exception {
        fileBean.dir = tmpFolder.newFile();

        validator.assertViolation("dir");
    }

    private static final class FileBean {
        @Directory
        private File dir;
    }
}