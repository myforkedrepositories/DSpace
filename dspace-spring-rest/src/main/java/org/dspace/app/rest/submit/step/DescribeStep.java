package org.dspace.app.rest.submit.step;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.app.rest.model.MetadataValueRest;
import org.dspace.app.rest.model.RestModel;
import org.dspace.app.rest.model.step.DataDescribe;
import org.dspace.app.rest.submit.AbstractRestProcessingStep;
import org.dspace.app.util.DCInput;
import org.dspace.app.util.DCInputSet;
import org.dspace.app.util.DCInputsReader;
import org.dspace.app.util.DCInputsReaderException;
import org.dspace.app.util.SubmissionStepConfig;
import org.dspace.content.MetadataValue;
import org.dspace.content.WorkspaceItem;
import org.dspace.content.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

public class DescribeStep extends org.dspace.submit.step.DescribeStep implements AbstractRestProcessingStep {

	private static final Logger log = Logger.getLogger(DescribeStep.class);

	private DCInputsReader inputReader;
	
	@Autowired
	private ItemService itemService;
	
	public DescribeStep() throws DCInputsReaderException {
		inputReader = new DCInputsReader();
	}
	
	@Override
	public RestModel getData(WorkspaceItem obj, SubmissionStepConfig config) {		
		DataDescribe data = new DataDescribe();
		try {
			DCInputSet inputConfig = inputReader.getInputsByFormName(config.getId());
			for(DCInput input : inputConfig.getFields()) {
				List<MetadataValue> mdv = itemService.getMetadataByMetadataString(obj.getItem(), input.getFieldName());
				for(MetadataValue md : mdv) {
					MetadataValueRest dto = new MetadataValueRest();
					dto.setAuthority(md.getAuthority());
					dto.setConfidence(md.getConfidence());
					dto.setLanguage(md.getLanguage());
					dto.setPlace(md.getPlace());
					dto.setValue(md.getValue());
					
					if(data.getMetadata().containsKey(md.getMetadataField().toString())) {
						data.getMetadata().get(md.getMetadataField().toString()).add(dto);
					}
					else {
						List<MetadataValueRest> listDto = new ArrayList<>();
						listDto.add(dto);
						data.getMetadata().put(md.getMetadataField().toString(), listDto);
					}
				}
			}
		} catch (DCInputsReaderException e) {
			log.error(e.getMessage(), e);
		}
		return data;
	}


}