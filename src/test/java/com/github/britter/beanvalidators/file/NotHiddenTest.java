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

public class NotHiddenTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();
    private FileBean fileBean;
    private ValidationWrapper<FileBean> validator;

    @Before
    public void setUp() {
        fileBean = new FileBean();
        validator = new ValidationWrapper<>(fileBean, "must not be hidden");
    }

    @Test
    public void shouldValidateNull() throws Exception {
        fileBean.file = null;

        validator.assertNoViolations("file");
    }

    @Test
    public void shouldValidateUnhiddenDirectory() throws Exception {
        fileBean.file = tmpFolder.newFolder();

        validator.assertNoViolations("file");
    }

    @Test
    public void shouldValidateUnhiddenFile() throws Exception {
        fileBean.file = tmpFolder.newFile();

        validator.assertNoViolations("file");
    }

    @Test
    public void shouldNotValidateHiddenDirectory() throws Exception {
        fileBean.file = tmpFolder.newFolder(".hidden");

        validator.assertViolation("file");
    }

    @Test
    public void shouldNotValidateHiddenFile() throws Exception {
        fileBean.file = tmpFolder.newFile(".hidden");

        validator.assertViolation("file");
    }

    private static final class FileBean {
        @NotHidden
        private File file;
    }
}