package com.castle.docs.support.elfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.castle.docs.entity.Admin;
import com.castle.docs.security.AuthenticationAuditorAware;

import br.com.trustsystems.elfinder.ElFinderConstants;
import br.com.trustsystems.elfinder.configuration.jaxb.ElfinderConfiguration;
import br.com.trustsystems.elfinder.core.Volume;
import br.com.trustsystems.elfinder.core.VolumeSecurity;
import br.com.trustsystems.elfinder.core.impl.DefaultVolumeSecurity;
import br.com.trustsystems.elfinder.core.impl.SecurityConstraint;
import br.com.trustsystems.elfinder.service.ElfinderStorage;
import br.com.trustsystems.elfinder.service.ElfinderStorageFactory;
import br.com.trustsystems.elfinder.service.VolumeSources;
import br.com.trustsystems.elfinder.service.impl.DefaultElfinderStorage;
import br.com.trustsystems.elfinder.service.impl.DefaultThumbnailWidth;
import br.com.trustsystems.elfinder.support.locale.LocaleUtils;

@Component("elfinderStorageFactory")
@Primary
public class CurrentUserElfinderStorageFactory implements ElfinderStorageFactory {

	@Autowired
	private AuthenticationAuditorAware authenticationAuditorAware;

	@Value("${file.storePath?:/Users/kongxiangxi/Documents/elfinder}")
	private String fileStorePath;

	private Map<Long, ElfinderStorage> CACHE = new HashMap<>();

	@Override
	public ElfinderStorage getVolumeSource() {

		Admin admin = authenticationAuditorAware.getCurrentAuditor();
		if (admin == null) {
			return new DefaultElfinderStorage();
		}

		ElfinderStorage elfinderStorage = CACHE.get(admin.getId());
		if (elfinderStorage == null) {
			elfinderStorage = initElfinderStorage(admin);
			CACHE.put(admin.getId(), elfinderStorage);
		}
		return elfinderStorage;
	}

	private ElfinderStorage initElfinderStorage(Admin admin) {
		int thumbnailWidth = 80;
		DefaultElfinderStorage defaultElfinderStorage = new DefaultElfinderStorage();

		// creates thumbnail
		DefaultThumbnailWidth defaultThumbnailWidth = new DefaultThumbnailWidth();
		defaultThumbnailWidth.setThumbnailWidth(thumbnailWidth);

		// creates volumes, volumeIds, volumeLocale and volumeSecurities
		Character defaultVolumeId = 'A';

		ElfinderConfiguration.Volume.Constraint readAndWritable = new ElfinderConfiguration.Volume.Constraint();
		readAndWritable.setLocked(false);
		readAndWritable.setReadable(true);
		readAndWritable.setWritable(true);

		ElfinderConfiguration.Volume.Constraint readOnly = new ElfinderConfiguration.Volume.Constraint();
		readOnly.setLocked(true);
		readOnly.setReadable(true);
		readOnly.setWritable(false);

		List<ElfinderConfiguration.Volume> elfinderConfigurationVolumes = new ArrayList<>();
		if (admin != null) {
			ElfinderConfiguration.Volume volumeSelf = new ElfinderConfiguration.Volume();
			volumeSelf.setSource("filesystem");
			volumeSelf.setAlias(admin.getName() + "的文件夹");
			volumeSelf.setPath(fileStorePath + "/admin/" + admin.getId());
			volumeSelf.setLocale("zh_CN");
			volumeSelf.setConstraint(readAndWritable);
			elfinderConfigurationVolumes.add(volumeSelf);
		}

		ElfinderConfiguration.Volume volumePublic = new ElfinderConfiguration.Volume();
		volumePublic.setSource("filesystem");
		volumePublic.setAlias("公共文件夹");
		volumePublic.setPath(fileStorePath + "/public");
		volumePublic.setLocale("zh_CN");
		volumePublic.setConstraint(readOnly);
		elfinderConfigurationVolumes.add(volumePublic);

		List<Volume> elfinderVolumes = new ArrayList<>(elfinderConfigurationVolumes.size());
		Map<Volume, String> elfinderVolumeIds = new HashMap<>(elfinderConfigurationVolumes.size());
		Map<Volume, Locale> elfinderVolumeLocales = new HashMap<>(elfinderConfigurationVolumes.size());
		List<VolumeSecurity> elfinderVolumeSecurities = new ArrayList<>();

		// creates volumes
		for (ElfinderConfiguration.Volume elfinderConfigurationVolume : elfinderConfigurationVolumes) {

			final String alias = elfinderConfigurationVolume.getAlias();
			final String path = elfinderConfigurationVolume.getPath();
			final String source = elfinderConfigurationVolume.getSource();
			final String locale = elfinderConfigurationVolume.getLocale();
			final boolean isLocked = elfinderConfigurationVolume.getConstraint().isLocked();
			final boolean isReadable = elfinderConfigurationVolume.getConstraint().isReadable();
			final boolean isWritable = elfinderConfigurationVolume.getConstraint().isWritable();

			// creates new volume
			Volume volume = VolumeSources.of(source).newInstance(alias, path);

			elfinderVolumes.add(volume);
			elfinderVolumeIds.put(volume, Character.toString(defaultVolumeId));
			elfinderVolumeLocales.put(volume, LocaleUtils.toLocale(locale));

			// creates security constraint
			SecurityConstraint securityConstraint = new SecurityConstraint();
			securityConstraint.setLocked(isLocked);
			securityConstraint.setReadable(isReadable);
			securityConstraint.setWritable(isWritable);

			// creates volume pattern and volume security
			final String volumePattern = Character.toString(defaultVolumeId) + ElFinderConstants.ELFINDER_VOLUME_SERCURITY_REGEX;
			elfinderVolumeSecurities.add(new DefaultVolumeSecurity(volumePattern, securityConstraint));

			// prepare next volumeId character
			defaultVolumeId++;
		}

		defaultElfinderStorage.setThumbnailWidth(defaultThumbnailWidth);
		defaultElfinderStorage.setVolumes(elfinderVolumes);
		defaultElfinderStorage.setVolumeIds(elfinderVolumeIds);
		defaultElfinderStorage.setVolumeLocales(elfinderVolumeLocales);
		defaultElfinderStorage.setVolumeSecurities(elfinderVolumeSecurities);

		return defaultElfinderStorage;
	}

}
