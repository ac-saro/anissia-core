package anissia.services

import anissia.dto.ResultData
import anissia.dto.TranslatorApplyDto
import anissia.dto.request.TranslatorApplyRequest
import anissia.misc.As
import anissia.rdb.domain.Agenda
import anissia.rdb.repository.AgendaPollsRepository
import anissia.rdb.repository.AgendaRepository
import javassist.Translator
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.time.LocalDateTime
import javax.persistence.*

@Service
class TranslatorService(
    private val agendaRepository: AgendaRepository,
    private val agendaPollsRepository: AgendaPollsRepository,
    private val sessionService: SessionService
) {
    private val code = "TRANSLATOR-APPLY"
    private val user get() = sessionService.session

    fun getApplyList(page: Int) =
            agendaRepository.findAllByCodeOrderByStatusDescAgendaNoDesc(code, PageRequest.of(page, 30)).map { TranslatorApplyDto(it) }

    fun getApply(applyNo: Long) =
            agendaRepository.findByIdOrNull(applyNo)?.takeIf { it.code == code }
                    ?.let { TranslatorApplyDto(it, true) }
                    ?: TranslatorApplyDto()

    fun createApply(translatorApplyRequest: TranslatorApplyRequest): ResultData<Long> {
        translatorApplyRequest.validate()
//        if (user?.isManager() == true) {
//            return ResultData("FAIL", "이미 권한이 있습니다.")
//        }

        agendaRepository.saveAndFlush(Agenda(
                code = code,
                status = "ACT",
                an = user!!.an,
                data1 = "ACT",
                data2 = user!!.name,
                data3 = translatorApplyRequest.website,
        )).run { return ResultData("OK", "", agendaNo) }
    }

    fun createApplyPoll() {

    }

    fun deleteApplyPoll() {

    }
}