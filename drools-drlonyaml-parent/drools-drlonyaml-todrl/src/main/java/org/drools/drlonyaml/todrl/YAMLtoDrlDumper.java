/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.drlonyaml.todrl;

import java.io.StringWriter;
import java.io.Writer;

import org.drools.drlonyaml.model.DrlPackage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class YAMLtoDrlDumper {
    public static final Configuration CONFIGURATION = config();
    
    private static Configuration config() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32); // this should ensure check of breaking changes on dependency update
        cfg.setClassForTemplateLoading(YAMLtoDrlDumper.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }
    
    public static String dumpDRL(DrlPackage drl) throws Exception {
        Template temp = CONFIGURATION.getTemplate("drl.ftl");

        Writer out = new StringWriter();
        temp.process(drl, out);
        String result = out.toString();
        out.close();
        return result;
    }
}
