package study.board.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.board.Service.MemberService;
import study.board.Service.NotificationServiceImpl;
import study.board.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final NotificationServiceImpl notificationService;
    private final MemberService memberService;

    @GetMapping(value = "/api/fcm/{memberId}",headers = "FCM-TOKEN")
    public void pushAlarmMyDevice(@PathVariable("memberId") Long memberId, @RequestHeader("FCM-TOKEN") String token){
       memberService.addFCMToken(memberId,token);
    }

    //MyPhone Test Push Alarm
    @GetMapping("/test/fcm")
    public void pushAlarmMyDevice(@RequestBody fcmData request){
        List<String> fcmToken = new ArrayList<>();
        fcmToken.add("eRoOilSNQui_AmvXxOevAS:APA91bFmqqgwMpsBiOPLQvmQhxVsRnuWMSntnFrT_Ff8yhCBgP19wf2ujM-rSqKDJ5Hc5ihwyNz8zxbxxX_PtUt5sIg3gDyyh8PPYbsPiBEtHdtIcBLCSOAFFHedRpQimcKO0RN19qlH");
        notificationService.sendByTokenList(fcmToken, request.title, request.body);
    }
    @Data
    static class fcmData{
        private String title;
        private String body;
    }
}
