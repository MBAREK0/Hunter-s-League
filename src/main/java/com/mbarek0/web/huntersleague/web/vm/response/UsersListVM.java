package com.mbarek0.web.huntersleague.web.vm.response;


import com.mbarek0.web.huntersleague.web.vm.request.RegisterVM;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsersListVM {
    private List<RegisterVM> users;
    private long totalElements;
    private int totalPages;
}