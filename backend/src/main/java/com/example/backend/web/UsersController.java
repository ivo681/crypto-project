package com.example.backend.web;

import com.example.backend.config.security.JwtAuthenticationResponse;
import com.example.backend.model.binding.UserLoginBindingModel;
import com.example.backend.model.binding.UserRegisterBindingModel;
import com.example.backend.model.service.OperationServiceModel;
import com.example.backend.model.service.UserRegisterServiceModel;
import com.example.backend.service.OrderService;
import com.example.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@RestController
@RequestMapping("/users")
//@CrossOrigin
public class UsersController {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final OrderService orderService;

    public UsersController(ModelMapper modelMapper, UserService userService, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> sendLoginDetails(
            @Valid @RequestBody UserLoginBindingModel userLoginBindingModel, Principal principal){
        JwtAuthenticationResponse response = this.userService.login(userLoginBindingModel);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> sendRegistrationDetails(
            @Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult){
        if (bindingResult.hasErrors() ||
                (Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18)){
            if (userRegisterBindingModel.getDateOfBirth() != null &&
                    Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.userService.emailExists(userRegisterBindingModel.getEmail())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        this.userService.registerAndLoginUser(this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/operations")
    public ResponseEntity<Object> getUserOperations(Principal principal){
        List<OperationServiceModel> operationServiceModels = this.orderService.getUserOperations(principal.getName());
        return new ResponseEntity(operationServiceModels, HttpStatus.OK);
    }

    @GetMapping("/all-operations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUserOperations(){
        List<OperationServiceModel> operationServiceModels = this.orderService.getAllUserOperations();
        return new ResponseEntity(operationServiceModels, HttpStatus.OK);
    }

    @GetMapping("/daily-operations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getDailyOperations(){
        List<OperationServiceModel> operationServiceModels = this.orderService.getUserOperationsFromToday();
        return new ResponseEntity(operationServiceModels, HttpStatus.OK);
    }

}
