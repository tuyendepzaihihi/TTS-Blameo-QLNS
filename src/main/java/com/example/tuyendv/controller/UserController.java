package com.example.tuyendv.controller;

import com.example.tuyendv.constant.Authority;
import com.example.tuyendv.dto.SalaryDTO;
import com.example.tuyendv.dto.UserDTO;
import com.example.tuyendv.entity.ERole;
import com.example.tuyendv.entity.Role;
import com.example.tuyendv.entity.User;
import com.example.tuyendv.payload.response.MessageResponse;
import com.example.tuyendv.payload.response.Response;
import com.example.tuyendv.service.RoleService;
import com.example.tuyendv.service.UserService;
import com.example.tuyendv.util.DateUtils;
import com.example.tuyendv.util.Utils;
import com.example.tuyendv.util.ValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    PasswordEncoder passEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    Utils utils;

    @GetMapping("/find-all")
    @PreAuthorize(Authority.SUPPER_ADMIN_AND_ADMIN)
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "1") Integer pageNo,
                                        @RequestParam(defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok().body(
                new Response<>(userService.findAllUser(pageNo, pageSize),
                        200, "Successfully!"));
    }

    @GetMapping("/saraly-user")
    public ResponseEntity<?> getSalaryUser(@RequestParam("month") Integer month,
                                           @RequestParam("year") Integer year) {
        try {
            return !ValueUtils.isMonth(month) || !ValueUtils.isYear(year) ?
                    ResponseEntity.badRequest().body(
                            new MessageResponse("Error: Month or year is wrong!"))
                    :
                    ResponseEntity.ok().body(
                            new Response<>(Math.round(userService.getSalary(month, year)),
                                    200, "Successfully!")
                    );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @GetMapping("/list-salary-user")
    @PreAuthorize(Authority.SUPPER_ADMIN)
    public ResponseEntity<?> getListSalaryUser(@RequestParam("month") Integer month,
                                               @RequestParam("year") Integer year,
                                               @RequestParam(defaultValue = "1") int pageNo,
                                               @RequestParam(defaultValue = "5") int pageSize) {
        try {
//            Pageable pageable = PageRequest.of(page, size);
//            if (pageable.getOffset() < userService.getListSalary(month, year).size()) {
//                int start = (int) pageable.getOffset();
//                int end = start + pageable.getPageSize() > userService.getListSalary(month, year).size() ?
//                        userService.getListSalary(month, year).size() : (int) (pageable.getOffset() + pageable.getPageSize());
                return !ValueUtils.isMonth(month) || !ValueUtils.isYear(year) ?
                        ResponseEntity.badRequest().body(
                                new MessageResponse("Error: Month or year is wrong!"))
                        :
                        ResponseEntity.ok().body(
                                new Response<>(userService.getListSalary(month, year).subList(pageNo, pageSize),
                                        200, "Successfully!")
                        );
            } else {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : List size min page number")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

//    @PostMapping("/create-user")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
//        try {
//            if(!ValueUtils.isEmail(dto.getEmail()) || ValueUtils.isPhone(dto.getPhoneNumber())){
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Check for email or phone number!"));
//            }
//            if(userService.existsByUserName(dto.getUsername())){
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//            }
//
//            if(userService.existsByEmail(dto.getEmail())){
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//            }
//
//            User user = new User();
//            BeanUtils.copyProperties(dto, user);
//            user.setDateOfBirth(DateUtils.toDateSql(dto.getDateOfBirth()));
//            user.setPassword(passEncoder.encode(dto.getPassword()));
//            Set<Role> roles = new HashSet<>();
//            roles.add(roleService.findByRole(ERole.ROLE_MEMBER.toString()).orElseThrow(
//                    () -> new RuntimeException("Role is not found!")
//            ));
//            user.setRoles(roles);
//            user.setStatus(1);
//            return ResponseEntity.ok().body(
//                    new Response<>(userService.save(user), 200, "User registered successfully!")
//            );
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO dto) {
        try {
            if (ValueUtils.isEmail(dto.getEmail())) {
                if (ValueUtils.isPhone(dto.getPhoneNumber())) {
                    User user = userService.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("User is not found!"));
                    user.setUserName(dto.getUsername());
                    user.setEmail(dto.getEmail());
                    user.setPassword(passEncoder.encode(dto.getPassword()));
                    user.setGender(dto.isGender());
                    user.setFullName(dto.getFullName());
                    user.setDateOfBirth(DateUtils.toDateSql(dto.getDateOfBirth()));
                    user.setPhoneNumber(dto.getPhoneNumber());
                    return ResponseEntity.ok().body(
                            new Response<>(userService.save(user), 200, "Successfully!")
                    );
                }
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone number invalidate!"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email invalidate!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/update-manager-user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateManagerUser(@RequestParam("user_id") Integer idUser,
                                               @RequestParam("manager_id") Integer idManager) {
        try {
            User user = userService.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User is not found!"));
            User manager = userService.findById(idManager)
                    .orElseThrow(() -> new RuntimeException("Manager is not found!"));

            List<Role> roleUser = new ArrayList<>(user.getRoles());
            List<Role> roleManager = new ArrayList<>(manager.getRoles());

            if (!roleUser.get(0).getRole().equals(ERole.ROLE_SUPER_ADMIN.toString())) {
                if (!roleUser.get(0).getRole().equals(ERole.ROLE_MEMBER.toString())) {
                    if (!roleManager.get(0).getRole().equals(ERole.ROLE_ADMIN.toString())) {
                        if ((roleManager.get(0).getRole().equals(ERole.ROLE_MEMBER.toString())))
                            return ResponseEntity.badRequest().body(new MessageResponse("Manager lower level!"));
                        user.setIdManager(idManager);
                        return ResponseEntity.ok().body(
                                new Response<>(userService.save(user), 200, "Successfully!")
                        );
                    }
                    return ResponseEntity.badRequest().body(new MessageResponse("Manager is not suitable!"));
                } else {
                    if (roleManager.get(0).getRole().equals(ERole.ROLE_MEMBER.toString()))
                        return ResponseEntity.badRequest().body(new MessageResponse("Manager is not suitable!"));
                    user.setIdManager(idManager);
                    return ResponseEntity.ok().body(
                            new Response<>(userService.save(user), 200, "Successfully!")
                    );
                }
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Can't add manager!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/update-role-user")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateRoleUser(@RequestParam("user_id") Integer idUser,
                                            @RequestParam("role_id") Integer idRole) {
        try {
            User user = userService.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User is not found!"));
            List<Role> roleList = new ArrayList<>(user.getRoles());
            if (roleList.get(0).getRole().equals(ERole.ROLE_MEMBER.toString())) {
                Set<Role> roles = new HashSet<>();
                roles.add(roleList.get(0));
                roles.add(roleService.findById(idRole).orElseThrow(
                        () -> new RuntimeException("Role is not found!")
                ));
                user.setRoles(roles);
                return ResponseEntity.ok().body(
                        new Response<>(userService.save(user), 200, "Successfully!")
                );
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Can't update role!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

    @PostMapping("/update-salary-user")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateSalaryUser(@RequestParam("user_id") Integer idUser,
                                              @RequestParam("salary") Double salary) {
        try {
            User user = userService.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User is not found!"));
            user.setSalary(salary);
            return ResponseEntity.ok().body(
                    new Response<>(userService.save(user), 200, "Successfully!")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
        }
    }

//    @PostMapping("/delete-user")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> deleteUser(@RequestParam("user_id") Integer id){
//        try {
//            User user = userService.findById(id)
//                    .orElseThrow(() -> new RuntimeException("User is not found!"));
//            user.setStatus(-1);
//            return ResponseEntity.ok().body(
//                    new Response<>(userService.save(user), 200, "Successfully!")
//            );
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
//        }
//    }

}
