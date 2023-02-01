package com.sgedts.wallet.service;

import com.sgedts.wallet.constant.Constant;
import com.sgedts.wallet.dto.ChangePasswordDTO;
import com.sgedts.wallet.dto.LoginDTO;
import com.sgedts.wallet.model.User;
import com.sgedts.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    static final String PASSWORD_FORMAT =
            "^.*(?=.{10,})(?=.+[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    static final String KTP_FORMAT= "^\\d{16}$";
    static final Pattern passwordPattern = Pattern.compile(PASSWORD_FORMAT);
    static final Pattern ktpPattern = Pattern.compile(KTP_FORMAT);
    public static boolean isValid(final String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }
    public static boolean isKtp(final String ktp){
        Matcher matcher = ktpPattern.matcher(ktp);
        return matcher.matches();
    }

    public void register(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1 != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
        }
        else{
            if (isValid(user.getPassword())){
                user.setTransactionLimit(Constant.MAX_TRANSACTION_AMOUNT);
                user.setIsBan(false);
                user.setBalance(0L);
                userRepository.save(user);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 10 characters and contains alphabet, number, symbol");
            }
        }
    }
    public void registerList(List<User> userList){
        userRepository.saveAll(userList);
    }
    public User getBalance(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Not Found");
        }
        else {
            return userRepository.findByUsername(username);
        }
    }

    public User getInfo(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Not Found");
        }
        else {
            return userRepository.findByUsername(username);
        }
    }

    public void unBan(User user){
        User result = userRepository.findByUsername(user.getUsername());
        result.setIsBan(false);
        result.setWrongCounter(0);
        userRepository.save(result);
    }

    public void login(LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (!loginDTO.getPassword().equals(user.getPassword())){
            var wrongCounter =user.getWrongCounter();
            if (wrongCounter==3){
                user.setIsBan(Boolean.TRUE);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Akun terblokir");
            }
            user.setWrongCounter(wrongCounter+1);
        }
        else{
            System.out.println("sukses login");
        }
        user.setWrongCounter(0);
        userRepository.save(user);
    }

    public void addKtp(String username, User user) {
//        User user1 = userRepository.findById(username).get();
        User user1 = userRepository.findByUsername(username);
        if (user1 == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Not Found");
        }
        else if (userRepository.findByKtp(user.getKtp()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KTP has been used by other user");
        }
        else {
            if (isKtp(user.getKtp())){
                user1.setKtp(user.getKtp());
                user1.setTransactionLimit(Constant.MAX_TRANSACTION_AMOUNT_WITH_KTP);
                userRepository.save(user1);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KTP terdiri dari 16 digit angka");
            }
        }
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO){
        User user = getInfo(changePasswordDTO.getUsername());
        String oldPassword = user.getPassword();
        if (!oldPassword.equals(changePasswordDTO.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password lama yang anda masukkan salah");
        }
        else if (changePasswordDTO.getNewPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password baru yang anda masukkan sama");
        }
        else {
            if (isValid(changePasswordDTO.getNewPassword())){
                user.setPassword(changePasswordDTO.getNewPassword());
                userRepository.save(user);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 10 characters and contains alphabet, number, symbol");
            }
        }
    }

}
