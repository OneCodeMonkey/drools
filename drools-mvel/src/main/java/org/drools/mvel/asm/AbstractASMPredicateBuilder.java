/*
 * Copyright (c) 2020. Red Hat, Inc. and/or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.mvel.asm;

import java.util.Map;

import org.drools.compiler.compiler.AnalysisResult;
import org.drools.compiler.compiler.BoundIdentifiers;
import org.drools.drl.ast.descr.PredicateDescr;
import org.drools.compiler.rule.builder.PredicateBuilder;
import org.drools.compiler.rule.builder.RuleBuildContext;
import org.drools.mvel.java.JavaRuleBuilderHelper;
import org.drools.base.rule.Declaration;
import org.drools.base.rule.PredicateConstraint;

public abstract class AbstractASMPredicateBuilder implements PredicateBuilder {
    public void build(final RuleBuildContext context,
                      final BoundIdentifiers usedIdentifiers,
                      final Declaration[] previousDeclarations,
                      final Declaration[] localDeclarations,
                      final PredicateConstraint predicateConstraint,
                      final PredicateDescr predicateDescr,
                      final AnalysisResult analysis) {

        final String className = "predicate" + context.getNextId();
        predicateDescr.setClassMethodName( className );

        final Map vars = JavaRuleBuilderHelper.createVariableContext(className,
                (String) predicateDescr.getContent(),
                context,
                previousDeclarations,
                localDeclarations,
                usedIdentifiers.getGlobals());

        JavaRuleBuilderHelper.generateMethodTemplate("predicateMethod", context, vars);

        byte[] bytecode = createPredicateBytecode(context, vars);
        JavaRuleBuilderHelper.registerInvokerBytecode(context, vars, bytecode, predicateConstraint);
    }

    protected abstract byte[] createPredicateBytecode(RuleBuildContext context, Map<String, Object> vars);
}
