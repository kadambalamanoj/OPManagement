package com.klef.project.services;

import java.util.List;

import javax.ejb.Remote;
import com.klef.project.models.Admin;

public interface AdminService 
{
  public Admin checkadminlogin(String username,String password);
  public long doctcount();
  public long reccount();
  public long patcount();
}
