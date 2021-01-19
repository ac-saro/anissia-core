package anissia.controller

import anissia.rdb.dto.ResultData
import anissia.rdb.dto.ResultStatus
import anissia.rdb.dto.Session
import anissia.services.SessionService
import anissia.rdb.dto.request.LoginRequest
import anissia.rdb.dto.request.LoginTokenRequest
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/session")
class SessionController(
        private val sessionService: SessionService
) {
    @PostMapping
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResultData<Session> = sessionService.doLogin(loginRequest)

    @PostMapping("/token")
    fun tokenLogin(@Valid @RequestBody loginTokenRequest: LoginTokenRequest): ResultData<Session> = sessionService.doTokenLogin(loginTokenRequest)

    @DeleteMapping
    fun logout(): ResultStatus = sessionService.doLogout()

    @GetMapping
    fun session(): Session = sessionService.session?: Session()
}
