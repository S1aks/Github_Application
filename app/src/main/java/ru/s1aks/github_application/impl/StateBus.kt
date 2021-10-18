package ru.s1aks.github_application.impl

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.s1aks.github_application.domain.entities.GithubUser
import java.lang.Error

enum class StateEvent {
    LOADING_STATE,
    SUCCESS_STATE,
    ERROR_STATE
}

class StateBus {
    private val bus = PublishSubject.create<StateEvent>()

    fun post(event: StateEvent) {
        bus.onNext(event)
    }

    fun get(): Observable<StateEvent> = bus
}