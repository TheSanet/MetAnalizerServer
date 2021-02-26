package com.jsanz.metanalizer.ws;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Value;

import com.jsanz.metanalizer.ws.entity.VersionRequest;
import com.jsanz.metanalizer.ws.entity.VersionResponse;


@WebService(endpointInterface = "com.jsanz.metanalizer.ws.Version")
public class Version implements IVersion{
	
	@Value("${version}")
	private String version;

	@Override
	public VersionResponse consultaVersion(VersionRequest request) throws DatatypeConfigurationException {
		VersionResponse responde=new VersionResponse();
		
		if(request.getFechaInicio()==null && request.getFechaFin()==null) {
			responde.setVersion(version);
		}

		return responde;
	}

	


}
