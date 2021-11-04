package com.ua.command;

import com.ua.command.add.*;
import com.ua.command.get.*;
import com.ua.command.update.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
	
	private static Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		
		commands.put("login", new LoginCommand());
		commands.put("addNewPatient", new AddNewPatientCommand());
		commands.put("checkNewLogin", new CheckNewLoginCommand());
		commands.put("createNewLogin", new AddNewLoginCommand());
		commands.put("exit", new FinishSessionComand());
		commands.put("archivePatient", new ListGenerationArchiveCommand());
		commands.put("archiveAppointment", new ListArchiveAppointmentCommand());
		commands.put("viewStaff", new ListGenerationDoctorCommand());
		commands.put("viewCaseRecord", new ListGenerationCaseRecordCommand());
		commands.put("viewNurse", new ListGenerationNurseCommand());
		commands.put("viewPatient", new ListGenerationPatientCommand());
		commands.put("redirect", new RedirectCommand());
		commands.put("sortDoctorList", new SortDoctorListCommand());
		commands.put("sortPatientList", new SortPatientListCommand());
		commands.put("deleteDoctor", new DeleteDoctorWithListCommand());
		commands.put("deleteNurse", new DeleteNurseCommand());
		commands.put("updateDoctor", new UpdateDoctorCommand());
		commands.put("updateNurse", new UpdateNurseCommand());
		commands.put("updatePatient", new UpdatePatientCommand());
        commands.put("doctorToPatient", new AppointDoctorToPatientCommand());
		commands.put("doctorAppointment", new DoctorAppointmentListCommand());
		commands.put("addAppointment", new AddAppointmentCommand());
		commands.put("deleteAppointment", new DeleteAppointmentCommand());
		commands.put("confirmAppointment", new ConfirmDoctorAppointmentCommand());
		commands.put("confirmNurseAppointment", new ConfirmNurseAppointmentCommand());
		commands.put("dischargedHospital", new DischargedHospitalCommand());


	}

	public static Command getCommand(String commandName) {
		return commands.get(commandName);
	}
	
}
