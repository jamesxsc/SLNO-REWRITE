package com.georlegacy.general.slno.configuration.validation.enumeration;

import com.georlegacy.general.slno.configuration.validation.util.Predicate;

public enum ValidatorRules {
    MAX("max", true, new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return false;
        }

        @Override
        public boolean test(String s, String arg) {
            return s.length() <= Integer.parseInt(arg);
        }
    }, "The value is too long"),
    MIN("min", true, new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return false;
        }

        @Override
        public boolean test(String s, String arg) {
            return s.length() >= Integer.parseInt(arg);
        }
    }, "The value is too short"),
    NUMBER("number", false, new Predicate<String>() {
        @Override
        public boolean test(String s, String arg) {
            return false;
        }

        @Override
        public boolean test(String s) {
            return s.replaceAll("[0-9]","").length() == 0;
        }
    }, "The value needs to be a number")
    ;

    private final String rule;
    private final boolean acceptsArgument;
    private final Predicate<String> validate;
    private String errorMessage;

    public boolean isAcceptsArgument() {
        return acceptsArgument;
    }

    public boolean validate(String toValidate) {
        return validate(toValidate, null);
    }

    public boolean validate(String toValidate, String arg) {
        if (isAcceptsArgument())
            return validate.test(toValidate, arg);
        else
            return validate.test(toValidate);
    }

    public String getRule() {
        return rule;
    }

    ValidatorRules(String rule, boolean acceptsArgument, Predicate<String> validate, String errorMessage) {
        this.rule = rule;
        this.acceptsArgument = acceptsArgument;
        this.validate = validate;
        this.errorMessage = errorMessage;
    }

    public static ValidatorRules parseValidatorRule(String rule) {
        if (rule.contains(":")) {
            for (ValidatorRules validatorRule : ValidatorRules.values())
                if (validatorRule.getRule().equals(rule.split(":")[0]))
                    return validatorRule;
        } else {
            for (ValidatorRules validatorRule : ValidatorRules.values())
                if (validatorRule.getRule().equals(rule))
                    return validatorRule;
        }
        return null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
