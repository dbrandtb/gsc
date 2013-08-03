<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <plan-vO xsi:type="java:mx.com.aon.portal.model.plan.PlanVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </plan-vO>
    </xsl:template>
    <xsl:template match="row">
        <xsl:if test="@CDRAMO">
			<xsl:element name="cd-ramo">
				<xsl:value-of select="@CDRAMO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDPLAN">
			<xsl:element name="cd-plan">
				<xsl:value-of select="@CDPLAN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSPLAN">
			<xsl:element name="ds-plan">
				<xsl:value-of select="@DSPLAN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSRAMO">
			<xsl:element name="ds-ramo">
				<xsl:value-of select="@DSRAMO"/>
			</xsl:element>
		</xsl:if>	
    </xsl:template>
</xsl:stylesheet>

