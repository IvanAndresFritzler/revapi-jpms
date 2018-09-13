/*
 * Copyright 2014-2018 Lukas Krejci
 * and other contributors as indicated by the @author tags.
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
package org.revapi.java.matcher;

import java.util.regex.Pattern;

import javax.lang.model.type.TypeMirror;

import org.revapi.ElementGateway;
import org.revapi.FilterMatch;
import org.revapi.java.spi.JavaAnnotationElement;
import org.revapi.java.spi.JavaModelElement;

/**
 * @author Lukas Krejci
 */
final class PatternExpression implements MatchExpression {
    private final DataExtractor<String> extractor;
    private final Pattern pattern;

    PatternExpression(DataExtractor<String> extractor, String pattern) {
        this.extractor = extractor;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public FilterMatch matches(ElementGateway.AnalysisStage stage, JavaModelElement element) {
        return test(extractor.extract(element));
    }

    @Override
    public FilterMatch matches(ElementGateway.AnalysisStage stage, JavaAnnotationElement annotation) {
        return test(extractor.extract(annotation));
    }

    @Override
    public FilterMatch matches(ElementGateway.AnalysisStage stage, TypeMirror type) {
        return test(extractor.extract(type));
    }

    @Override
    public FilterMatch matches(ElementGateway.AnalysisStage stage, AnnotationAttributeElement attribute) {
        return test(extractor.extract(attribute));
    }

    @Override
    public FilterMatch matches(ElementGateway.AnalysisStage stage, TypeParameterElement typeParameter) {
        return test(extractor.extract(typeParameter));
    }
    
    private FilterMatch test(String data) {
        return FilterMatch.fromBoolean(pattern.matcher(data).matches());
    }
}
