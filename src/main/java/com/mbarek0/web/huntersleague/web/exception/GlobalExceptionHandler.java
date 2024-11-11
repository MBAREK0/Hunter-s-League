package com.mbarek0.web.huntersleague.web.exception;

import com.mbarek0.web.huntersleague.web.exception.competition.*;
import com.mbarek0.web.huntersleague.web.exception.user.*;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.management.relation.RoleNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /** --------------------------------------- User Exceptions  */
    //  ---------------  UserNameAlreadyExistsException
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleUserNameAlreadyExistsException(
            UserNameAlreadyExistsException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    //  ---------------  UsernameOrPasswordInvalidException
    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleUsernameOrPasswordInvalidException(
            UsernameOrPasswordInvalidException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    //  ---------------  RoleNotFoundException
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    //  ---------------  UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    /** --------------------------------------- Competition Exceptions  */
    //  ---------------  CompetitionAlreadyExistsException
    @ExceptionHandler(CompetitionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleCompetitionAlreadyExistsException(
            CompetitionAlreadyExistsException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    // --------------- CompetitionNotFoundException
    @ExceptionHandler(CompetitionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleCompetitionNotFoundException(
            CompetitionNotFoundException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    // ---------------  OnlyOneCompetitionCanBeScheduledPerWeekException
    @ExceptionHandler(OnlyOneCompetitionCanBeScheduledPerWeekException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleOnlyOneCompetitionCanBeScheduledPerWeekException(
            OnlyOneCompetitionCanBeScheduledPerWeekException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    // ---------------  ParticipantLimitsException
    @ExceptionHandler(ParticipantLimitsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleParticipantLimitsException(
            ParticipantLimitsException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // ---------------  CompetitionIsNotOpenForRegistrationException
    @ExceptionHandler(CompetitionIsNotOpenForRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleCompetitionIsNotOpenForRegistrationException(
            CompetitionIsNotOpenForRegistrationException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // ---------------  HuntWeightException
    @ExceptionHandler(HuntWeightException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleHuntWeightException(
            HuntWeightException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    /** --------------------------------------- Global Exceptions  */

    //  ---------------  FieldCannotBeNullException
    @ExceptionHandler(FieldCannotBeNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleFieldCannotBeNullException(
            FieldCannotBeNullException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

//    //  ---------------  Exception
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("message", "Internal Server Error");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
//    }
//
//    //  ---------------  IllegalArgumentException
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("message", "Bad Request");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
//    }

}
