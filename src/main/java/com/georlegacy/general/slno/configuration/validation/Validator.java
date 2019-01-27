package com.georlegacy.general.slno.configuration.validation;

import com.georlegacy.general.slno.configuration.validation.enumeration.ValidatorRules;

import java.util.LinkedList;
import java.util.List;

public class Validator {

    public static List<ValidatorRules> validate(String toValidate, String rules) {
        return validate(toValidate, rules.split("|"));
    }

    public static List<ValidatorRules> validate(String toValidate, String[] rules) {
        List<ValidatorRules> failedRules = new LinkedList<>();
        for (String rule : rules) {
            ValidatorRules validatorRule = ValidatorRules.parseValidatorRule(rule);
            if (validatorRule == null) {

                continue;
            }
            if (!(validatorRule.isAcceptsArgument() ? validatorRule.validate(toValidate,
                    rule.split(":", 2)[1]) : validatorRule.validate(toValidate))) {
                failedRules.add(validatorRule);
            }
        }
        return failedRules;
    }

}
