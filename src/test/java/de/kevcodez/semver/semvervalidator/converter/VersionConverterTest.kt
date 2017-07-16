package de.kevcodez.semver.semvervalidator.converter

import com.github.zafarkhaja.semver.Version
import org.junit.Assert.assertEquals
import org.junit.Test

class VersionConverterTest {

    private val versionConverter = VersionConverterImpl()

    @Test
    fun convertToDto() {
        val version = Version.valueOf("1.0.0-rc.1+build.1")

        val versionDto = versionConverter.convertToDto(version)
        assertEquals(version.majorVersion, versionDto.major)
        assertEquals(version.minorVersion, versionDto.minor)
        assertEquals(version.patchVersion, versionDto.patch)
        assertEquals(version.buildMetadata, versionDto.buildMetadata)
        assertEquals(version.normalVersion, versionDto.normal)
        assertEquals(version.preReleaseVersion, versionDto.preRelease)
    }

}