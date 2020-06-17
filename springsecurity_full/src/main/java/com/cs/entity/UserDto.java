package com.cs.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author: szh
 * @since:
 */
@Setter
@Getter
public class UserDto extends User2 {
    private Set<Role2> roles;
}
