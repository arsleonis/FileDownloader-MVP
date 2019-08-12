package com.zrz.android.filedownloader.model.db.data

import android.net.Uri
import com.zrz.android.filedownloader.BuildConfig.*
import com.zrz.android.filedownloader.entity.PDFResource

object FirstDataCreator {
    private val urlAddresses = arrayOf(
        "https://www.xeroxscanners.com/downloads/Manuals/XMS/PDF_Converter_Pro_Quick_Reference_Guide.RU.pdf",
        "https://files.canon-europe.com/files/soft29417/manual/EOS_350D_IM_RUS_toc.pdf",
        "https://www-support-downloads.sonymobile.com/lt25i/userguide_EN_LT25i_4_Android4.3.pdf",
        "https://www.fujifilm.com/support/digital_cameras/manuals/pdf/index/x/fujifilm_xt1_manual_ru.pdf",
        "https://www.americanexpress.com/content/dam/amex/us/staticassets/pdf/GCO/Test_PDF.pdf",
        "https://www.telc.net/fileadmin/user_upload/telc_english_b2_uebungstest_1.pdf",
        "https://www.ets.org/Media/Tests/GRE/pdf/gre_research_validity_data.pdf",
        "https://ncu.rcnpv.com.tw/Uploads/20131231103232738561744.pdf",
        "https://help.adobe.com/archive/en_US/acrobat/8/standard/acrobat_8_help.pdf",
        "https://yourielts.ru/images/files/IELTS-Download-Test-pdf/Reading_Test.pdf",
        "https://www.provisu.ch/images/PDF/Snellenchart_en.pdf",
        "https://www.iso.nl/website/wp-content/uploads/2018/03/pdf-sample-3.pdf",
        "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf",
        "https://www.cms.gov/Regulations-and-Guidance/Legislation/CLIA/downloads/waivetbl.pdf",
        "https://www.englisch-hilfen.de/en/download/test_simple_present_en.pdf",
        "https://www.kekstcnc.com/umbraco/Surface/Download/DownloadById/1637?fileName=Test%20Pdf%20File.pdf",
        "https://files.stroyinf.ru/Data2/1/4294851/4294851950.pdf",
        "https://www.euro-test.ru/Pub.Lib/Normativ_docs/GOST22733.pdf",
        "https://careers.epam.by/content/dam/epam/by/book_epam_by/Software_Testing_Basics_2_izdanie.pdf",
        "https://www.cambridgeenglish.org/latinamerica/Images/165873-yle-sample-papers-flyers-vol-1.pdf"
    )

    fun getFirstData(): ArrayList<PDFResource> {
        val pDFResourcesArray = ArrayList<PDFResource>()
        for (urlAddress in urlAddresses) {
            val fileName = Uri.parse(urlAddress).lastPathSegment!!
            val pDFResource = PDFResource(
                urlAddress,
                fileName,
                FILE_DIR_PATH,
                NO_IMAGE,
                THUMBNAILS_DIR_PATH
            )
            pDFResourcesArray.add(pDFResource)
        }
        return pDFResourcesArray
    }
}