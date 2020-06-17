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
public class RoleDto extends  Role2{
    private Set<User2> users;
}
