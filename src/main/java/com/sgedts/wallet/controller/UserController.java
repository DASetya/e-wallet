package com.sgedts.wallet.controller;

import com.sgedts.wallet.dto.ChangePasswordDTO;
import com.sgedts.wallet.dto.GetBalanceDTO;
import com.sgedts.wallet.dto.GetInfoDTO;
import com.sgedts.wallet.dto.LoginDTO;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.NumberFormat;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    public GetBalanceDTO modelToDto(User user){
        GetBalanceDTO getBalanceDTO = new GetBalanceDTO();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        getBalanceDTO.setBalance(numberFormat.format(user.getBalance()));
        getBalanceDTO.setTransactionLimit(user.getTransactionLimit());
        return getBalanceDTO;
    }

    @PostMapping("/registration")
    public void register(@RequestBody User user){
        userService.register(user);
    }
    @GetMapping("/{username}/getbalance")
    public ResponseEntity<GetBalanceDTO> getBalance(@PathVariable String username){
        User user = userService.getBalance(username);
        return ResponseEntity.ok().body(modelToDto(user));
    }
    @GetMapping("/{username}/getinfo")
    public ResponseEntity<GetInfoDTO> getInfo(@PathVariable String username){
        User user = userService.getInfo(username);
        GetInfoDTO getInfoDTO = modelMapper.map(user, GetInfoDTO.class);
        return ResponseEntity.ok().body(getInfoDTO);
    }
    @PutMapping("/{username}/unban")
    public void unBan(@PathVariable String username){
        User user = userService.getInfo(username);
        user.setIsBan(false);
        userService.unBan(user);
    }
    @PostMapping("/login")
    public void login(@RequestBody LoginDTO loginDTO){
        userService.login(loginDTO);
    }
    @PutMapping("/{username}/addktp")
    public void updateUser(@PathVariable String username, @RequestBody User user){
        userService.addKtp(username, user);
    }
    @PostMapping("/changepassword")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changePassword(changePasswordDTO);
    }
}
