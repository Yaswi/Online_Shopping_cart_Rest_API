package com.orderManagement.menu;

import java.sql.SQLException;
import com.orderManagement.exceptions.*;

public interface Menu{
   public void displayMenu() throws SQLException, ItemAlreadyExists;
}
