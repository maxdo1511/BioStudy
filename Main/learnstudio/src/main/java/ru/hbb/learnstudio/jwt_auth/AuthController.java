package ru.hbb.learnstudio.jwt_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hbb.learnstudio.jwt_auth.Data.SigninRequest;
import ru.hbb.learnstudio.jwt_auth.Data.SignupRequest;
import ru.hbb.learnstudio.user.UserRepository;
import ru.hbb.learnstudio.user.UserEntity;
import ru.hbb.learnstudio.user.enums.UserRole;
import ru.hbb.learnstudio.user.utils.DateUtils;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsUserByUsername(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name!");
        }
        if (userRepository.existsUserByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different email");
        }
        String hashed = passwordEncoder.encode(signupRequest.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setPassword(hashed);
        userEntity.setRole(UserRole.DEFAULT.toString());
        Date date = new Date();
        userEntity.setRegisterDate(date.getTime());
        userEntity.setLastAuth(-1);
        userRepository.save(userEntity);
        return ResponseEntity.ok("Success!");
    }
    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        }catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        UserEntity userEntity = userRepository.findUserByUsername(signinRequest.getUsername()).orElseThrow();
        Date date = new Date();
        userEntity.setLastAuth(date.getTime());
        userRepository.save(userEntity);
        return ResponseEntity.ok(jwt);
    }
}
