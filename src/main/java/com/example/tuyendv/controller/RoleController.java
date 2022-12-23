//package com.example.tuyendv.controller;
//
//import com.example.tuyendv.dto.RoleDTO;
//import com.example.tuyendv.entity.Role;
//import com.example.tuyendv.payload.response.MessageResponse;
//import com.example.tuyendv.payload.response.Response;
//import com.example.tuyendv.service.RoleService;
//import com.example.tuyendv.util.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/role")
//public class RoleController {
//
//    @Autowired
//    RoleService roleService;
//
//    @Autowired
//    Utils utils;
//
//    @GetMapping("/list-role")
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> getAllRole(){
//        try{
//            return ResponseEntity.ok().body(new Response<>(roleService.findAll(), 200, "Successfully!"));
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/create-role")
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> createRole(@RequestBody Role entity){
//        try{
//            if(utils.checkRoleExist(entity.getRole())){
//                return ResponseEntity.ok().body(new Response<>(roleService.save(entity), 200, "Successfully!"));
//            }
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is already in use!"));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/update-role")
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> updateRole(@RequestBody RoleDTO dto){
//        try{
//            Role entity = roleService.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Role is not found!"));
//            entity.setRole(dto.getRole());
//            entity.setStatus(1);
//            return ResponseEntity.ok().body(new Response<>(roleService.save(entity), 200, "Successfully!"));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/delete-role")
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<?> deleteRole(@RequestParam("role_id") Integer id){
//        try{
//            Role entity = roleService.findById(id).orElseThrow(() -> new RuntimeException("Role is not found!"));
//            entity.setStatus(-1);
//            return ResponseEntity.ok().body(new Response<>(roleService.save(entity), 200, "Successfully!"));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(new MessageResponse("Error : " + e.getMessage()));
//        }
//    }
//
//}
