package com.gap.metrics.util;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class NamespacePrefixMapperImpl extends NamespacePrefixMapper {
    public String getPreferredPrefix(String namespaceUri, String suggestion,
                                     boolean requirePrefix) {
        if ("http://service.gap.com/EBOSchemas/PromoMarketingRecommendedPriceEBO"
                .equals(namespaceUri)) {
            return "ebo";
        }

        if ("http://service.gap.com/schemas/ESBHeader".equals(namespaceUri)) {
            return "esb";
        }
        if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri)) {
            return "xsi";
        }

        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {
                "http://service.gap.com/EBOSchemas/PromoMarketingRecommendedPriceEBO",
                "http://service.gap.com/schemas/DC",
                "http://www.w3.org/2001/XMLSchema-instance" };
    }

}
