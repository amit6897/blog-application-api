package com.blogging.services;

import com.blogging.config.AppConstants;
import com.blogging.entities.Role;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.RoleRepository;
import com.blogging.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        //encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // roles
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.repository.save(user);
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.repository.save(user);
        return this.usertoDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "User", " id ", userId));
        // user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.repository.save(user);
        UserDto userToDto = this.usertoDto(updatedUser);
        return userToDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                "User", " id ", userId));
        return this.usertoDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List <User> list = this.repository.findAll();
        List <UserDto> userDto = list.stream().map(l ->
                this.usertoDto(l)).collect(Collectors.toList());
        return userDto;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.repository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException( "User", " id ", userId));
        this.repository.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user= this.modelMapper.map(userDto,User.class);
        return user;
    }

    public UserDto usertoDto(User user) {
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

    /*
    public User dtoToUser(UserDto userDto) {
        User user= new User();
	    user.setId(userDto.getId());
	    user.setAbout(userDto.getAbout());
	    user.setEmail(userDto.getEmail());
	    user.setName(userDto.getName());
	    user.setPassword(userDto.getPassword());

	    return user;
    }

    public UserDto userToDto(User user) {
	    UserDto userDto = new UserDto();
	    userDto.setAbout(user.getAbout());
	    userDto.setEmail(user.getEmail());
	    userDto.setId(user.getId());
	    userDto.setName(user.getName());
	    userDto.setPassword(user.getPassword());
        return userDto;
    }
    */
}
