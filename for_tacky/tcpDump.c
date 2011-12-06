#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <net/if.h>
#include <netinet/in.h>
#include <netinet/if_ether.h>
#include <pcap.h>
#define SIZE_ETHERNET 14
#define ETHER_ADDR_LEN 6
struct struct_ethernet {
	u_char  ether_dhost[ETHER_ADDR_LEN];
	u_char  ether_shost[ETHER_ADDR_LEN];
	u_short ether_type;

};
struct struct_ip{
    unsigned int   ip_hl:4; /* both fields are 4 bits */
    unsigned int   ip_v:4;
    uint8_t        ip_tos;
    uint16_t       ip_len;
    uint16_t       ip_id;
    uint16_t       ip_off;
    uint8_t        ip_ttl;
    uint8_t        ip_p;
    uint16_t       ip_sum;
    u_int          ip_src;
    u_int          ip_dst;
};
struct struct_tcp{
    u_short       tcp_srp;
    u_short       tcp_dsp;
    uint32_t       tcp_seq;
    uint32_t       tcp_ackn;
    uint8_t        tcp_offRes;//offset and reserved
    uint8_t        tcp_ackFlag;
    uint8_t        tcp_wSize;
};
main(int argc, char *argv[]) {
	pcap_t *pd;
	int snaplen = 64;
    int pflag = 0;
    int timeout = 1000;
    char ebuf[PCAP_ERRBUF_SIZE];
    bpf_u_int32 localnet, netmask;
    pcap_handler callback;
    void print_ethaddr(u_char *, const struct pcap_pkthdr *, const u_char *packet);
    struct bpf_program fp;
    
	//mac縺ｪ繧影n0縺ｨ縺丘buntu縺ｪ繧影th1縺ｨ縺
    if ((pd = pcap_open_live("en1", snaplen, !pflag, timeout, ebuf)) == NULL) {
		exit(1);
    }	
    
	if (pcap_lookupnet("en1", &localnet, &netmask, ebuf) < 0) {
		exit(2);
    }
    if(pcap_compile(pd,&fp,"port 80",0,netmask) == -1)
    { 
        fprintf(stderr,"Error calling pcap_compile\n"); exit(1); 
    }
     
    if(pcap_setfilter(pd,&fp) == -1)
    { 
        fprintf(stderr,"Error setting filter\n"); exit(1); 
    }
    
    
    callback = print_ethaddr;
    if (pcap_loop(pd, -1, callback, NULL) < 0) {
		exit(3);
    }
	pcap_close(pd);
	exit(0);
}
char* ProtocolName(int id){
    char* name[]= {"ICMP","IGMP","GGP","IP","ST","","TCP","CBT","CBT","EGP","IGP","BBN-RCC","","","","","","","UDP","","",""};
    if(id<18)return name[id];
    return "UNKNOWN";
}
void printIP(u_int ip){
    printf(" %d.%d.%d.%d ",(ip)%256,(ip/(256))%256,(ip/(256*256))%256,ip/(256*256*256));
}

void print_ethaddr(u_char *args, const struct pcap_pkthdr *header, const u_char *packet){
    const struct struct_ethernet *eh;        
    const struct struct_ip *ipHeader;
    const struct struct_tcp *tcp_h;
	eh = (struct struct_ethernet *)(packet);
    int version;
    int ipheaderlen;
    int ttl,protocol;
    ipHeader= (struct struct_ip *)(packet+SIZE_ETHERNET);
    
	int i;
	ipheaderlen= ((ipHeader->ip_hl))*4;
    if(ipheaderlen<20) return;
    //fittle icp protocol
    //if(ipHeader->ip_p!=17) return;
    
    tcp_h= (struct struct_tcp *)(packet+SIZE_ETHERNET+ipheaderlen);
    
    printf("MAC:");
    for (i = 0; i < 6; ++i) {
		printf("%02x", (int)eh->ether_shost[i]);
		if(i < 5){
			printf(":");
		}
	}
    printf(" -> ");
    
    for (i = 0; i < 6; ++i) {
		printf("%02x", (int)eh->ether_dhost[i]);
		if(i < 5){
			printf(":");
		}
	}
    //version= (ipHeader->ip_vpVerHLen)>>4;
    //ttl=(ipHeader->ip_ttl)>>8;
    //protocol = ((ipHeader->ip_ttl)<<8)>>8;
    printIP(ipHeader->ip_src);printIP(ipHeader->ip_dst); printf("\n");
    //printf("%s %s\n",inet_ntoa(ipHeader->ip_src),inet_ntoa(ipHeader->ip_dst));
	printf("\tEtherType:%3d ver:%2d hl:%3d, id= %8d len=%6d ttl:%2d \n\tProtocol:%d\n   ",eh->ether_type,ipHeader->ip_v,ipheaderlen,ipHeader->ip_id,(ipHeader->ip_len),ipHeader->ip_ttl,ipHeader->ip_p);
    
    printf("\tTCP: srPort=%d dsPort=%d %X\n",ntohs(tcp_h->tcp_srp),ntohs(tcp_h->tcp_dsp),tcp_h->tcp_ackFlag);
}
