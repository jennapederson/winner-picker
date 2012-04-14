package winner.picker

import org.springframework.dao.DataIntegrityViolationException

class WinnerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static Random random = new Random()
	
    def index() {
        redirect(action: "list", params: params)
    }
	
	def pick() {
		Winner winnerInstance = pickWinner()
		if (winnerInstance == null) {
			flash.message = message(code: 'default.noWinner.message', args: [message(code: 'winner.label', default: 'Winner')])
		} else {
			flash.message = message(code: 'default.winner.message', args: [winnerInstance.firstName, winnerInstance.lastName, message(code: 'winner.label', default: 'Winner')])
		}
		redirect(action: "list", params: params)
	}
	
	def pickWinner() {
		def potentialWinners = Winner.list().findAll { winner ->
			!winner.alreadyAWinner
		}
		if (potentialWinners.size() < 1) {
			return null
		}
		int num = getNextRandomNumber(potentialWinners.size())
		def winner = potentialWinners[num]
//		winner.alreadyAWinner = true
		return winner
	}
	
	def getNextRandomNumber(int maxWinners) {
		random.nextInt(maxWinners)
	}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [winnerInstanceList: Winner.list(params), winnerInstanceTotal: Winner.count()]
    }

    def create() {
        [winnerInstance: new Winner(params)]
    }

    def save() {
        def winnerInstance = new Winner(params)
        if (!winnerInstance.save(flush: true)) {
            render(view: "create", model: [winnerInstance: winnerInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'winner.label', default: 'Winner'), winnerInstance.id])
        redirect(action: "show", id: winnerInstance.id)
    }

    def show() {
        def winnerInstance = Winner.get(params.id)
        if (!winnerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "list")
            return
        }

        [winnerInstance: winnerInstance]
    }

    def edit() {
        def winnerInstance = Winner.get(params.id)
        if (!winnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "list")
            return
        }

        [winnerInstance: winnerInstance]
    }

    def update() {
        def winnerInstance = Winner.get(params.id)
        if (!winnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (winnerInstance.version > version) {
                winnerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'winner.label', default: 'Winner')] as Object[],
                          "Another user has updated this Winner while you were editing")
                render(view: "edit", model: [winnerInstance: winnerInstance])
                return
            }
        }

        winnerInstance.properties = params

        if (!winnerInstance.save(flush: true)) {
            render(view: "edit", model: [winnerInstance: winnerInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'winner.label', default: 'Winner'), winnerInstance.id])
        redirect(action: "show", id: winnerInstance.id)
    }

    def delete() {
        def winnerInstance = Winner.get(params.id)
        if (!winnerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "list")
            return
        }

        try {
            winnerInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'winner.label', default: 'Winner'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
