package com.JOSE.Inventario.Controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JOSE.Inventario.DTO.LoginRequestDto;
import com.JOSE.Inventario.DTO.LoginResponseDto;
import com.JOSE.Inventario.DTO.UserSecResponseDto;
import com.JOSE.Inventario.Service.IUserService;

@RestController
@RequestMapping("/inventario-app")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private IUserService userService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Backend is running");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            
           // esto solo es la autenticacion
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            
            
            
            
            
            
            
            //guarda los datos del usuario para quenguarde el estado de eautenticacion del usuario 
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //esctructura de respuesta 
            UserSecResponseDto user = userService.buscarUsuarioPorUsername(loginRequest.getUsername());
            LoginResponseDto response = new LoginResponseDto();
            response.setToken("Bearer " + loginRequest.getUsername() + "_" + System.currentTimeMillis());
            response.setUsername(loginRequest.getUsername());
            response.setId(user.getId());
            response.setRoles(user.getRoles().stream()
                .map(role -> role.getRole())
                .collect(Collectors.toList()));
            response.setMessage("Login exitoso");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error inesperado: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    

    @PostMapping("/login/user")
    public ResponseEntity<?> loginuser(@RequestBody LoginRequestDto loginRequest) {
        try {
            if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Usuario y contraseña son requeridos");
            }
            UserSecResponseDto user = userService.buscarUsuarioPorUsername(loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LoginResponseDto response = new LoginResponseDto();
            response.setToken("Bearer " + loginRequest.getUsername() + "_" + System.currentTimeMillis());
            response.setUsername(loginRequest.getUsername());
            response.setId(user.getId());
            response.setRoles(user.getRoles().stream()
                .map(role -> role.getRole())
                .collect(Collectors.toList()));
            response.setMessage("Login exitoso");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error inesperado: " + e.getMessage());
        }
    }
}




