package com.sgedts.wallet.service;

import com.sgedts.wallet.constant.Constant;
import com.sgedts.wallet.dto.ChangePasswordDTO;
import com.sgedts.wallet.entity.User;
import com.sgedts.wallet.repository.UserRepository;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    static final String PASSWORD_FORMAT =
            "^.*(?=.{10,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    static final Pattern pattern = Pattern.compile(PASSWORD_FORMAT);
    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
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
                userRepository.save(user);
                Response response = new Response();
                response.setMessage("OK");
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 10 characters and contains alphabet, number, symbol");
            }
        }
    }
//    public List<User> getAllUser() {
//        return userRepository.findAll();
//    }

    public User getInfo(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Not Found");
        }
        else {
            return userRepository.findByUsername(username);
        }
    }

    public User addKtp(String username, User user) {
//        User user1 = userRepository.findById(username).get();
        User user1 = userRepository.findByUsername(username);
        if (user1 == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Not Found");
        }
        else if (userRepository.findByKtp(user.getKtp()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KTP has been used by other user");
        }
        else {
            user1.setKtp(user.getKtp());
            user1.setTransactionLimit(Constant.MAX_TRANSACTION_AMOUNT_WITH_KTP);
            return userRepository.save(user1);
        }
    }

    public User changePassword(ChangePasswordDTO changePasswordDTO){
        User user1 = getInfo(changePasswordDTO.getUsername());
        String oldPassword = user1.getPassword();
        if (!oldPassword.equals(changePasswordDTO.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password lama yang anda masukkan salah");
        }
        else if (changePasswordDTO.getNewPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password baru yang anda masukkan sama");
        }
        else {
            if (isValid(changePasswordDTO.getNewPassword())){
                user1.setPassword(changePasswordDTO.getNewPassword());
                return userRepository.save(user1);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 10 characters and contains alphabet, number, symbol");
            }
        }
    }

}
