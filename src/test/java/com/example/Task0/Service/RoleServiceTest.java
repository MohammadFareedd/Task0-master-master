package com.example.Task0.Service;

import com.example.Task0.dao.RoleRepository;
import com.example.Task0.entity.Role;
import com.example.Task0.service.RoleServiceimp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceimp roleServiceimp;
    @Test
    public void findRoleByName_Test(){
        Role r1=new Role("Admin");
        String name="admin";
        when(roleRepository.findRoleByName(Mockito.anyString())).thenReturn(r1);
        Role temp=roleServiceimp.findByRoleName(name);
        assertThat(temp).isNotNull();

    }


}
