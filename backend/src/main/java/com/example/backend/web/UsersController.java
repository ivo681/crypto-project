package com.example.backend.web;

import com.example.backend.config.security.JwtAuthenticationResponse;
import com.example.backend.model.binding.UserLoginBindingModel;
import com.example.backend.model.binding.UserRegisterBindingModel;
import com.example.backend.model.service.UserRegisterServiceModel;
import com.example.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UsersController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

//    @GetMapping("/login")
//    public void test(@RequestBody UserLoginBindingModel userLoginBindingModel){
//        System.out.println(userLoginBindingModel.getEmail());
//        System.out.println("success");
//    }

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
        Map<String, List<String>> errors = new HashMap<>();
        if (bindingResult.hasErrors() ||
                (Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18)){
            if (userRegisterBindingModel.getDateOfBirth() != null &&
                    Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
                bindingResult.rejectValue("dateOfBirth", "error.userRegisterBindingModel", "You must be at least 18 years old to register");
            }
            for (FieldError error:bindingResult.getFieldErrors()){
                if (!errors.containsKey(error.getField())){
                    errors.put(error.getField(), new ArrayList<>());
                }
                List<String> messages = errors.get(error.getField());
                messages.add(error.getDefaultMessage());
                errors.put(error.getField(), messages);
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (this.userService.emailExists(userRegisterBindingModel.getEmail())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        this.userService.registerAndLoginUser(this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RequestMapping("/login")
//    public boolean login(@RequestBody User user) {
//        return
//                user.getEmail().equals("user@abv.bg") && user.getPassword().equals("password");
//    }

//    @RequestMapping("/user")
//    public Principal user(HttpServletRequest request) {
//        String authToken = request.getHeader("Authorization")
//                .substring("Basic".length()).trim();
//        return () ->  new String(Base64.getDecoder()
//                .decode(authToken)).split(":")[0];
//    }
}
