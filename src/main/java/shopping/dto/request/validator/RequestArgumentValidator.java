package shopping.dto.request.validator;

import shopping.exception.ArgumentValidateFailException;

public class RequestArgumentValidator {

    private RequestArgumentValidator() {
    }

    public static void validateStringArgument(String target, String targetName, int maxLength) {

        if (target == null || target.isBlank()) {
            throw new ArgumentValidateFailException(targetName + " 항목은 비어있으면 안됩니다.");
        }
        
        if (target.length() > maxLength) {
            throw new ArgumentValidateFailException(
                    String.format("%s 항목은 %d 글자 이하여야 합니다. 현재 입력 값 : '%s'",
                            targetName,
                            maxLength,
                            target));
        }
    }

    public static void validateNumberArgument(Number target, String targetName) {
        validateNotNull(target, targetName);
        validatePositive(target, targetName);
    }

    private static void validateNotNull(Object target, String targetName) {
        if (target == null) {
            throw new ArgumentValidateFailException(targetName + " 항목은 비어있으면 안됩니다.");
        }
    }

    private static void validatePositive(Number target, String targetName) {
        if (target.longValue() <= 0L) {
            throw new ArgumentValidateFailException(
                    targetName + " 항목은 양수여야 합니다. 현재 입력 값 : " + target);
        }
    }


}
