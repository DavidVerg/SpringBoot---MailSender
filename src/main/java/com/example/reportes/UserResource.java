package com.example.reportes;

import com.example.reportes.model.User;
import com.example.reportes.service.EmailServiceImpl;
import com.example.reportes.service.ParseTemplate;
import com.example.reportes.service.UserService;
import com.lowagie.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserResource {

    private final UserService userService;
    private final EmailServiceImpl emailService;
    private final ParseTemplate parseTemplate;

    public UserResource(UserService userService, EmailServiceImpl emailService, ParseTemplate parseTemplate) {
        this.userService = userService;
        this.emailService = emailService;
        this.parseTemplate = parseTemplate;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/report")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.getAllUsers();

        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);
    }

    @GetMapping("/PDF-Template")
    public void templateToPdf(Model model) throws IOException {
        parseTemplate.generatePdfFromHtml(parseTemplate.parseThymeleafTemplate("thymeleaf_template"));
    }


    @GetMapping("/email")
    public void mailSend() throws MessagingException {

        emailService.sendMessageWithAttachment();
    }

    @GetMapping("/email/{id}")
    public void mailSend(@PathVariable("id") Long id) throws MessagingException, IOException {

        User usuario = userService.getUserById(id);
        //emailService.sendMessageFromTemplate(usuario, parseTemplate.parseThymeleafMailTemplate(id));
        emailService.sendMessageFromTemplate(usuario, parseTemplate.parseThymeleafTemplate("thymeleaf_template"));
    }


}
